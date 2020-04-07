package ru.rus.crud.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rus.crud.domain.data.MergeRequest;
import ru.rus.crud.domain.data.RiskProfile;
import ru.rus.crud.domain.entity.Client;
import ru.rus.crud.repository.ClientRepository;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/client")
public class CrudEndpoint {

    private final ClientRepository repository;
    private HttpServletRequest request;

    @Autowired
    public CrudEndpoint(ClientRepository repository, HttpServletRequest request) {
        this.repository = repository;
        this.request = request;
    }

    // По идее не хорошо гонять Entity на фронт, но тут вообще нет смысла заводить отдельный класс
    @GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> get(@PathVariable("id") Long clientId) {
        Optional<Client> client = repository.findById(clientId);
        return client
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Long> add(@RequestParam("riskProfile") RiskProfile riskProfile) {

        if (riskProfile != null)
            repository.save(new Client(riskProfile));
        else
            return ResponseEntity
                    .badRequest()
                    .build();

        return ResponseEntity
                .created(URI.create(request.getRequestURI()))
                .build();
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Object> update(@RequestBody Client clientForUpdate) {
        Optional<Client> clientEntity = repository.findById(clientForUpdate.getId());

        if (clientEntity.isPresent()) {
            Client client = clientEntity.get();
            client.setRiskProfile(clientForUpdate.getRiskProfile());

            return ResponseEntity.ok().build();
        } else
            // Еще можно создавать нового пользователя, зависит от задачи
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        boolean clientExists = repository.existsById(id);
        if (clientExists) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/merge")
    public ResponseEntity<Object> merge(@RequestBody MergeRequest mergeRequest) {
        if(mergeRequest == null || mergeRequest.getMergeClients() == null || mergeRequest.getMergeClients().isEmpty())
            return ResponseEntity.badRequest().build();

        // Для тех идентификаторов, для которых не будет существовать сущности, будем просто игнорировать
        Iterable<Client> clients = repository.findAllById(mergeRequest.getMergeClients());
        Optional<RiskProfile> maxRisk = StreamSupport.stream(clients.spliterator(), false)
                .map(Client::getRiskProfile)
                .max(Comparator.comparingInt(RiskProfile::getOrdinal));

        repository.deleteAll(clients);

        // Поле riskProfile в таблице обязательно, точно что-то найдем
        repository.save(new Client(maxRisk.get()));

        return ResponseEntity.ok().build();
    }
}
