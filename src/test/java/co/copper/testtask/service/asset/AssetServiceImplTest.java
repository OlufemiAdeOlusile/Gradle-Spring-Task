package co.copper.testtask.service.asset;


import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.external.ExternalApproveService;
import co.copper.testtask.model.Asset;
import co.copper.testtask.repository.AssetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

/**
 * @author olufemi on 4/10/22
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ExternalApproveService.class})
public class AssetServiceImplTest {

    private AssetDto asset_dto;

    @InjectMocks
    private AssetServiceImpl under_test;

    @Autowired
    private ExternalApproveService approveService;

    private Asset asset;

    @Mock
    private AssetRepository assetRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        asset_dto = new AssetDto();
        asset_dto.setId(1234L);
        asset_dto.setName("Test_Name");
        asset_dto.setYear((short) 2018);
        asset_dto.setCurrency("USD");
        asset_dto.setValue(BigDecimal.valueOf(223450033));

        asset = new Asset();

        under_test = new AssetServiceImpl(approveService, assetRepository);
    }

    @Test
    public void test_approve_should_be_approved_with_correct_dto() {
        //When
        boolean approve = under_test.approve(asset_dto);

        //Then
        Assertions.assertTrue(approve);
    }

    @Test
    public void test_approve_should_not_be_approved_with_wrong_year() {
        //Given
        asset_dto.setYear((short) 1999);

        //When
        boolean approve = under_test.approve(asset_dto);

        //Then
        Assertions.assertFalse(approve);
    }

    @Test
    public void test_approve_should_not_be_approved_with_wrong_value() {
        //Given
        asset_dto.setValue(BigDecimal.valueOf(2234));

        //When
        boolean approve = under_test.approve(asset_dto);

        //Then
        Assertions.assertFalse(approve);
    }


    @Test
    public void test_save_should_save_and_return_saved_asset() {
        //When
        doReturn(asset).when(assetRepository).save(asset);
        Asset saved_asset = under_test.save(asset);
        //under_test.load(asset.getId());

        //Then
        Mockito.verify(assetRepository, Mockito.times(1)).save(Mockito.any());
        Assertions.assertEquals(asset, saved_asset);
    }

    @Test
    public void test_load_should_load_and_return_saved_id() {
        //Given
        asset.setId(1234L);

        //When
        doReturn(Optional.of(1234L)).when(assetRepository).findById(asset.getId());
        Optional<Asset> loaded_id = under_test.load(1234L);
        //under_test.load(asset.getId());

        //Then
        Mockito.verify(assetRepository, Mockito.times(1)).findById(Mockito.any());
        Assertions.assertEquals(1234L, loaded_id.get());
    }

    @Test
    public void test_fromDto_should_always_contain_correct_asset() {
        //Given
        asset.setId(1234L);
        asset.setName("Test_Name");
        asset.setYear((short) 2018);
        asset.setCurrency("USD");
        asset.setValue(BigDecimal.valueOf(223450033));

        //When
        Asset from_dto_asset = under_test.fromDto(asset_dto);

        //Then
        Assertions.assertEquals(asset, from_dto_asset);
    }

    @Test
    public void test_toDto_should_always_contain_correct_assetDto() {
        //Given
        asset.setId(1234L);
        asset.setName("Test_Name");
        asset.setYear((short) 2018);
        asset.setCurrency("USD");
        asset.setValue(BigDecimal.valueOf(223450033));

        //When
        AssetDto to_dto = under_test.toDTO(asset);

        //Then
        Assertions.assertEquals(asset_dto, to_dto);
    }

    @Test
    public void test_getId_should_always_return_id() {
        //Given
        asset.setId(1234L);

        //When
        Long id = under_test.getId(asset);

        //Then
        Assertions.assertEquals(1234L, id);
    }

}
