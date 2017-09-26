package com.mentoring.web;

import javax.validation.Valid;

import com.mentoring.service.jms.ProcessCardProducer;
import com.mentoring.web.dto.card.CardDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ivanovaolyaa
 * @version 9/26/2017
 */
@RestController("/cards")
public class CardController {

    @Autowired
    private ProcessCardProducer processCardProducer;

    @PostMapping
    public void saveCard(@RequestBody @Valid final CardDto dto) throws Exception {
        processCardProducer.submitCardDetails(dto);
    }

}
