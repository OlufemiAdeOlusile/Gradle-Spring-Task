package co.copper.testtask.service;

import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.dto.Collateral;
import co.copper.testtask.external.ExternalApproveService;
import co.copper.testtask.model.Asset;
import co.copper.testtask.repository.AssetRepository;
import co.copper.testtask.service.asset.AssetService;
import co.copper.testtask.service.asset.AssetServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@ContextConfiguration(classes = {ExternalApproveService.class})
@ExtendWith(SpringExtension.class)
public class CollateralServiceTest {

    private AssetDto asset_dto;

    @InjectMocks
    private CollateralService under_test;

    private AssetService assetService;

    @Mock
    private AssetRepository assetRepository;

    @Autowired
    private ExternalApproveService approveService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        asset_dto = new AssetDto();
        asset_dto.setId(1234L);
        asset_dto.setName("Test_Name");
        asset_dto.setYear((short) 2018);
        asset_dto.setCurrency("USD");
        asset_dto.setValue(BigDecimal.valueOf(223450033));

        assetService = new AssetServiceImpl(approveService, assetRepository);
    }


    @Test
    public void test_saveCollateral_should_return_correct_id() {
        //GIVEN
        under_test = spy(new CollateralService(assetService));
        Asset asset = assetService.fromDto(asset_dto);

        //When
        doReturn(asset).when(assetRepository).save(asset);
        Long id = under_test.saveCollateral(asset_dto);

        //Then
        Assertions.assertEquals(asset_dto.getId(), id);
    }


    @Test
    public void test_saveCollateral_should_throw_exception_with_instance_of_collateral() {
        //GIVEN
        under_test = spy(new CollateralService(assetService));
        Asset asset = assetService.fromDto(asset_dto);

        //When
        doReturn(asset).when(assetRepository).save(asset);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            under_test.saveCollateral(new testCollateralClass());
        });

        //Then
        System.out.println(exception.toString());

        String expectedMessage = "java.lang.IllegalArgumentException";
        String actualMessage = exception.toString();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }



    public static class testCollateralClass implements Collateral {
        private Long id;
    }
}



