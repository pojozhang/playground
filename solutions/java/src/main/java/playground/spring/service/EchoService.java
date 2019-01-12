package playground.spring.service;

import org.springframework.stereotype.Service;

@Service
public class EchoService {

    public String echo(String string) {
        return string;
    }
}
