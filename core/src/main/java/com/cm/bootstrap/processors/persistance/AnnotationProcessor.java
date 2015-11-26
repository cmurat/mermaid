package com.cm.bootstrap.processors.persistance;

import com.cm.exception.MermaidCoreException;

/**
 * Created by Çelebi Murat on 26/11/15.
 */
public interface AnnotationProcessor {
    public void process(AnnotationProcessorManager processorManager) throws MermaidCoreException;
}
