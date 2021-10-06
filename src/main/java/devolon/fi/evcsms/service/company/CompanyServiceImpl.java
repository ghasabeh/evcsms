package devolon.fi.evcsms.service.company;

import devolon.fi.evcsms.mapper.CompanyMapper;
import devolon.fi.evcsms.model.dto.CompanyDto;
import devolon.fi.evcsms.model.entity.CompanyEntity;
import devolon.fi.evcsms.model.entity.CompanyTreePathMaker;
import devolon.fi.evcsms.repository.CompanyRepository;
import devolon.fi.evcsms.utils.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyTreePathMaker companyTreePathMaker;

    @Override
    public Long create(CompanyDto dto) {
        CompanyEntity companyEntity = companyMapper.map(dto);
        companyTreePathMaker.prePersist(companyEntity);
        return companyRepository.save(companyEntity).getId();
    }

    @Override
    public void update(CompanyDto dto) {
        CompanyEntity companyEntity = companyMapper.map(dto);
        companyTreePathMaker.preUpdate(companyEntity);
        companyRepository.save(companyEntity);
    }

    @Override
    public CompanyDto findById(Long id) {
        return companyMapper.map(companyRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
