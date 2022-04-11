package co.copper.testtask.service.asset;

import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.external.ExternalApproveService;
import co.copper.testtask.model.Asset;
import co.copper.testtask.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private ExternalApproveService approveService;

    @Autowired
    private AssetRepository assetRepository;

    public AssetServiceImpl(ExternalApproveService approveService, AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
        this.approveService = approveService;
    }

    @Override
    public boolean approve(AssetDto dto) {
        return approveService.approve(new AssetAdapter(dto)) == 0;
    }

    @Override
    public Asset save(Asset asset) {
        return assetRepository.save(asset);
    }

    @Override
    public Optional<Asset> load(Long id) {
        return assetRepository.findById(id);
    }

    @Override
    public Asset fromDto(AssetDto dto) {
        return new Asset(
                dto.getId(),
                dto.getName(),
                dto.getCurrency(),
                dto.getYear(),
                dto.getValue()
        );
    }

    @Override
    public AssetDto toDTO(Asset asset) {
        return new AssetDto(
                asset.getId(),
                asset.getName(),
                asset.getCurrency(),
                asset.getYear(),
                asset.getValue()
        );
    }

    @Override
    public Long getId(Asset asset) {
        return asset.getId();
    }
}
