package rv.aric.security.config.runner;

import org.springframework.stereotype.Component;
import rv.aric.ipsum.enums.EnumEntityRunner;
import rv.aric.security.enums.EnumTokenType;
import rv.aric.security.repository.TokenTypeRepository;

@Component
public class EnumTokenTypeRunner implements EnumEntityRunner {

    TokenTypeRepository tokenTypeRepository;

    public EnumTokenTypeRunner(TokenTypeRepository tokenTypeRepository) {
        this.tokenTypeRepository = tokenTypeRepository;
    }

    @Override
    public void run() {
        this.tokenTypeRepository.save(EnumTokenType.BEARER.get());
    }
}
