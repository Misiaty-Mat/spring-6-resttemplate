package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void testDeleteBeer() {
        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("12.39"))
                .beerName("Mega Pops 2")
                .beerStyle(BeerStyle.GOSE)
                .quantityOnHand(999)
                .upc("123456")
                .build();

        BeerDTO beerDTO = beerClient.createBeer(newDto);

        beerClient.deleteBeer(beerDTO.getId());

        assertThrows(HttpClientErrorException.class, () -> {
            beerClient.getBeerById(beerDTO.getId());
        });
    }

    @Test
    void testUpdateBeer() {
        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("12.39"))
                .beerName("Mega Pops 2")
                .beerStyle(BeerStyle.GOSE)
                .quantityOnHand(999)
                .upc("123456")
                .build();

        BeerDTO beerDTO = beerClient.createBeer(newDto);

        final String newName = "Mega Pops 3";
        beerDTO.setBeerName(newName);
        BeerDTO updatedBeer = beerClient.updateBeer(beerDTO);

        assertEquals(newName, updatedBeer.getBeerName());
    }

    @Test
    void testCreateBeer() {
        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("12.39"))
                .beerName("Mega Pops")
                .beerStyle(BeerStyle.GOSE)
                .quantityOnHand(999)
                .upc("123456")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDto);
        assertNotNull(savedDto);
    }

    @Test
    void testGetBeerById() {
        Page<BeerDTO> beerDTOS = beerClient.listBeers();

        BeerDTO dto = beerDTOS.getContent().get(0);

        BeerDTO byId = beerClient.getBeerById(dto.getId());

        assertNotNull(byId);
    }

    void listBeersNoBeerName() {
        beerClient.listBeers(null, null, null, null, null);
    }

    @Test
    void listBeers() {
        beerClient.listBeers("ALE", null, null, null, null);
    }
}