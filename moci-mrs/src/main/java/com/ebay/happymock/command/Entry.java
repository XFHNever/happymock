package com.ebay.happymock.command;

import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkState;

/**
 * CLI main entry
 * User: jicui
 * Date: 14-10-27
 */
public class Entry {
    public static void main(String[] args) throws Exception {
        checkState(args.length > 0);
        HappyMockCommand happyMock=new HappyMockCommand();
        happyMock.parse(args);
        happyMock.run();
    }

}
