package ru.rus.crud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rus.crud.domain.data.RiskProfile;
import ru.rus.crud.domain.entity.Client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ClientMergerTest {

	@Test
	void testClientRiskProfileMax() {
		List<Client> clients = new ArrayList<>();
		clients.add(new Client(RiskProfile.LOW));
		clients.add(new Client(RiskProfile.NORMAL));
		clients.add(new Client(RiskProfile.HIGH));

		Optional<RiskProfile> max = clients.stream().map(Client::getRiskProfile)
				.max(Comparator.comparingInt(RiskProfile::getOrdinal));

		assert max.isPresent() && max.get().equals(RiskProfile.HIGH);
	}

}
