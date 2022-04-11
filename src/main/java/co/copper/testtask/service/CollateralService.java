package co.copper.testtask.service;

import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.dto.Collateral;
import co.copper.testtask.service.asset.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


// TODO: reimplement this
@Service
public class CollateralService {
    @Autowired
    private AssetService assetService;

    protected CollateralService(AssetService assetService) {
        this.assetService = assetService;
    }

    @SuppressWarnings("ConstantConditions")
    public Long saveCollateral(Collateral object) {
        if (!(object instanceof AssetDto)) {
            throw new IllegalArgumentException();
        }

        AssetDto asset = (AssetDto) object;
        boolean approved = assetService.approve(asset);
        if (!approved) {
            return null;
        }

        return Optional.of(asset)
                .map(assetService::fromDto)
                .map(assetService::save)
                .map(assetService::getId)
                .orElse(null);
    }

    public Collateral getInfo(String id) {
        return Optional.of(Long.parseLong(id))
                .flatMap(assetService::load)
                .map(assetService::toDTO)
                .orElse(null);
    }
}
