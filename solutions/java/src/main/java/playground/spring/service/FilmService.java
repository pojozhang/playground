package playground.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import playground.spring.entity.FilmEntity;
import playground.spring.repository.FilmRepository;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(FilmEntity filmEntity) {
        filmRepository.save(filmEntity);
        TransactionStatus transactionStatus = TransactionAspectSupport.currentTransactionStatus();
        System.out.println(transactionStatus.isNewTransaction());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create2(FilmEntity filmEntity) {
        filmRepository.save(filmEntity);
        TransactionStatus transactionStatus = TransactionAspectSupport.currentTransactionStatus();
        System.out.println(transactionStatus.isNewTransaction());
    }
}
