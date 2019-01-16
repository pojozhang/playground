package playground.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.interceptor.TransactionAspectSupport.currentTransactionStatus;

@Service
public class TransactionService {

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionStatus required(Runnable runnable) {
        runnable.run();
        return currentTransactionStatus();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransactionStatus requiresNew(Runnable runnable) {
        runnable.run();
        return currentTransactionStatus();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public TransactionStatus mandatory(Runnable runnable) {
        runnable.run();
        return currentTransactionStatus();
    }

    @Transactional(propagation = Propagation.NESTED)
    public TransactionStatus nested(Runnable runnable) {
        runnable.run();
        return currentTransactionStatus();
    }

    @Transactional(propagation = Propagation.NEVER)
    public TransactionStatus never(Runnable runnable) {
        runnable.run();
        return currentTransactionStatus();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TransactionStatus notSupported(Runnable runnable) {
        runnable.run();
        return currentTransactionStatus();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public TransactionStatus supports(Runnable runnable) {
        runnable.run();
        return currentTransactionStatus();
    }
}
