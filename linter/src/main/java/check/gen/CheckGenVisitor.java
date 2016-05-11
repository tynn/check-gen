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

import com.android.tools.lint.detector.api.JavaContext;

import lombok.ast.ForwardingAstVisitor;
import lombok.ast.ImportDeclaration;

import static check.gen.CheckGenDetector.BUILD_CONFIG;

public class CheckGenVisitor extends ForwardingAstVisitor {
    private static final String MSG = "Importing external BuildConfig.";
    private static final String NAME = ".BuildConfig";

    private final JavaContext mContext;

    public CheckGenVisitor(JavaContext context) {
        mContext = context;
    }

    @Override
    public boolean visitImportDeclaration(ImportDeclaration node) {
        String pkg = mContext.getMainProject().getPackage();
        if (pkg != null) {
            String name = node.asFullyQualifiedName();
            if (name.endsWith(NAME) && !name.equals(pkg + NAME)) {
                mContext.report(BUILD_CONFIG, node, mContext.getLocation(node), MSG);
            }
        }
        return false;
    }
}
