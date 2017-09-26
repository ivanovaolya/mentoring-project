package com.mentoring.service.impl;

import com.mentoring.domain.entity.Card;
import com.mentoring.domain.repository.CardRepository;
import com.mentoring.service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ivanovaolyaa
 * @version 9/26/2017
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void saveCard(final Card card) {
        cardRepository.save(card);
    }

}
