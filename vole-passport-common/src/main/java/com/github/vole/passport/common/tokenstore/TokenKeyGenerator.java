package com.github.vole.passport.common.tokenstore;

import com.github.vole.passport.common.token.PassportToken;

public interface TokenKeyGenerator {

    String extractKey(PassportToken token);
}
