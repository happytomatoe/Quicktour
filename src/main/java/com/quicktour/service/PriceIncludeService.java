package com.quicktour.service;

import com.quicktour.entity.PriceDescription;
import com.quicktour.repository.PriceIncludeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PriceIncludeService {

    @Autowired
    private PriceIncludeRepository priceIncludeRepository;

    public List<PriceDescription> findAll() {
        return priceIncludeRepository.findAll();
    }
}
