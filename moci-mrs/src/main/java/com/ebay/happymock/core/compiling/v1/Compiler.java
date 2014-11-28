package com.ebay.happymock.core.compiling.v1;

import com.ebay.happymock.core.compiling.v1.exception.CompileException;


/**
 * A interface of the compiler and subclass should provide below functions to describe the compiler.
 * Typically a valid compile contains
 * functions:
 * 1)compile a dsl
 * 2)find child compiler
 * 3)return compile name
 *
 * User: jicui
 * Date: 14-8-22
 */
public interface Compiler extends Cloneable {

     static final int PROCESSED=1;
     static final int UNPROCESSED=0;
    /**
     * Compile the given DSL
     *
     * @param dsl
     * @throws CompileException
     */
    public void compile(String dsl) throws CompileException;

    /**
     * compile keyword
     *
     * @return
     */
    public String getKeyword();

    /**
     * Find the nested compiler
     *
     * @return
     */
   public Compiler locateChild(String keyword);



}
