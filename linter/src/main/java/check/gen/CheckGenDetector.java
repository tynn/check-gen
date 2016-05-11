/*
 * Copyright 2016 Christian Schmitz
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

package check.gen;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Speed;

import java.util.Arrays;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.ImportDeclaration;
import lombok.ast.Node;

import static com.android.tools.lint.detector.api.Category.CORRECTNESS;
import static com.android.tools.lint.detector.api.Severity.ERROR;

public class CheckGenDetector extends Detector implements Detector.JavaScanner {
    private static final String NAME = "CheckGenBuildConfig";
    private static final String DESC = "Importing external BuildConfig class";
    private static final String EXP = "A BuildConfig class from a library was imported. This can't be right.";

    public static final Issue BUILD_CONFIG = Issue.create(NAME, DESC, EXP, CORRECTNESS, 8,
            ERROR, new Implementation(CheckGenDetector.class, Scope.JAVA_FILE_SCOPE));

    @Override
    public Speed getSpeed() {
        return Speed.FAST;
    }

    @Override
    public AstVisitor createJavaVisitor(@NonNull JavaContext context) {
        return new CheckGenVisitor(context);
    }

    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return Arrays.<Class<? extends Node>>asList(ImportDeclaration.class);
    }
}
