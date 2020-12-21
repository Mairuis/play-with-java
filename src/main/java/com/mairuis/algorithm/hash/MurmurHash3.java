package com.mairuis.algorithm.hash;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * @author Mairuis
 * @since 2020/12/20
 */
public class MurmurHash3 implements HashFunc<String> {
    private final HashFunction hashFunction;

    public MurmurHash3() {
        this.hashFunction = Hashing.murmur3_32();
    }

    @Override
    public int hash(String param) {
        return hashFunction.hashUnencodedChars(param).asInt();
    }
}
