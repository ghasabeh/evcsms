package devolon.fi.evcsms.service.company;

import devolon.fi.evcsms.mapper.CompanyMapper;
import devolon.fi.evcsms.model.dto.CompanyDto;
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

    @Override
    public Long create(CompanyDto dto) {
        return companyRepository.save(companyMapper.map(dto)).getId();
    }

    @Override
    public void update(CompanyDto dto) {
        companyRepository.save(companyMapper.map(dto));
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
