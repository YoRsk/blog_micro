package com.lrm.searchms.search.messaging;

import com.lrm.searchms.search.dto.SearchMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class SearchMessageProducer {
    private final RabbitTemplate rabbitTemplate;
    public SearchMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendSearchMessage(String searchContent) {
        SearchMessage searchMessage = new SearchMessage();
        searchMessage.setContent(searchContent);
        rabbitTemplate.convertAndSend("searchBlogQueue", searchMessage);
    }
}
