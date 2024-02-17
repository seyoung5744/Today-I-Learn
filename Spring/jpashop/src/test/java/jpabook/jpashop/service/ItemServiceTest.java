package jpabook.jpashop.service;

import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 아이템_저장() {
        //given
        Item item = new Item();
        item.setName("itemA");
        item.setPrice(10000);
        item.setStockQuantity(10);

        //when
        itemService.saveItem(item);

        //then
        Assertions.assertThat(itemRepository.findOne(item.getId())).isEqualTo(item);
    }
}