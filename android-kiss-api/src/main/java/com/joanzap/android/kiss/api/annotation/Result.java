/**
 * Copyright 2014 Joan Zapata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joanzap.android.kiss.api.annotation;

import com.joanzap.android.kiss.api.BaseEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Result {

    Class<? extends BaseEvent> value() default USE_PARAMETER_TYPE.class;

    Sender from() default Sender.THIS;

    /** This is the default value for event(), do not use it. */
    static final class USE_PARAMETER_TYPE extends BaseEvent {}

    static enum Sender {
        THIS, ALL
    }
}
