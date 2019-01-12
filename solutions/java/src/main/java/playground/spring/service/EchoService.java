package playground.spring.service;

import org.springframework.stereotype.Service;
import playground.spring.annotation.Log;

import java.util.Objects;

@Service
public class EchoService {

    @Log
    public String echo(String string) {
        Objects.requireNonNull(string);
        return string;
    }
}
