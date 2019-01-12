package playground.spring.service;

import org.springframework.stereotype.Service;
import playground.spring.annotation.Log;

@Service
public class EchoService {

    @Log
    public String echo(String string) {
        return string;
    }
}
