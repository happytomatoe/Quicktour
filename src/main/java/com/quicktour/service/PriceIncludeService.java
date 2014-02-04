package com.quicktour.service;

import com.quicktour.entity.PriceDescription;
import com.quicktour.repository.PriceIncludeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: student
 * Date: 11.12.13
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */

@Service
@Transactional
public class PriceIncludeService {

    @Autowired
    private PriceIncludeRepository priceIncludeRepository;

    public List<PriceDescription> findAll() {
        return priceIncludeRepository.findAll();
    }

    public PriceDescription findByDescription(String description) {
        return priceIncludeRepository.findByDescription(description);
    }

}
