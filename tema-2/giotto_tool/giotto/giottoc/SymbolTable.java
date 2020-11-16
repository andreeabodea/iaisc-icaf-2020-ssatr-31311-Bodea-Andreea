/*

Copyright (c) 2001-2004 The Regents of the University of California.
All rights reserved.

Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.

GIOTTO_COPYRIGHT_VERSION_2
COPYRIGHTENDKEY
*/

package giotto.giottoc;

import giotto.giottoc.analysis.DepthFirstAdapter;
import giotto.giottoc.node.AActuatorDeclaration;
import giotto.giottoc.node.ADriverDeclaration;
import giotto.giottoc.node.AHostDeclaration;
import giotto.giottoc.node.AModeDeclaration;
import giotto.giottoc.node.AModuleDeclaration;
import giotto.giottoc.node.ANetworkDeclaration;
import giotto.giottoc.node.AOutputDeclaration;
import giotto.giottoc.node.ASensorDeclaration;
import giotto.giottoc.node.ATaskDeclaration;
import giotto.giottoc.node.ATypeDeclaration;
import giotto.giottoc.node.NodeCast;
import giotto.giottoc.node.Token;

import java.io.File;
import java.util.Map;

/**

This class implements a symbol table.

@author Christoph Kirsch
@version SymbolTable.java,v 1.8 2004/09/29 03:52:30 cxh Exp
@since Giotto 1.0.1
*/
public class SymbolTable extends DepthFirstAdapter {
    public final Map declarations =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map modules =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map types =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map sensorPorts =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map actuatorPorts =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map outputPorts =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map tasks =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map drivers =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map modes =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map hosts =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public final Map networks =
    new TypedTreeMap(StringComparator.instance, StringCast.instance, NodeCast.instance);

    public File pkgDir;

    public SymbolTable(File pkgDir) {
        this.pkgDir = pkgDir;
    }

    // Action code

    public void outAModuleDeclaration(AModuleDeclaration node) {
        final String name = node.getModuleName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getModuleName(), name);
        }

        if (modules.put(name, node) != null) {
            errorRedefined(node.getModuleName(), name);
        }
    }

    public void outATypeDeclaration(ATypeDeclaration node) {
        final String name = node.getTypeName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getTypeName(), name);
        }

        if (types.put(name, node) != null) {
            errorRedefined(node.getTypeName(), name);
        }
    }

    public void outASensorDeclaration(ASensorDeclaration node) {
        final String name = node.getPortName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getPortName(), name);
        }

        if (sensorPorts.put(name, node) != null) {
            errorRedefined(node.getPortName(), name);
        }
    }

    public void outAActuatorDeclaration(AActuatorDeclaration node) {
        final String name = node.getPortName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getPortName(), name);
        }

        if (actuatorPorts.put(name, node) != null) {
            errorRedefined(node.getPortName(), name);
        }
    }

    public void outAOutputDeclaration(AOutputDeclaration node) {
        final String name = node.getPortName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getPortName(), name);
        }

        if (outputPorts.put(name, node) != null) {
            errorRedefined(node.getPortName(), name);
        }
    }

    public void outATaskDeclaration(ATaskDeclaration node) {
        final String name = node.getTaskName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getTaskName(), name);
        }

        if (tasks.put(name, node) != null) {
            errorRedefined(node.getTaskName(), name);
        }
    }

    public void outADriverDeclaration(ADriverDeclaration node) {
        final String name = node.getDriverName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getDriverName(), name);
        }

        if (drivers.put(name, node) != null) {
            errorRedefined(node.getDriverName(), name);
        }
    }

    public void outAModeDeclaration(AModeDeclaration node) {
        final String name = node.getModeName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getModeName(), name);
        }

        if (modes.put(name, node) != null) {
            errorRedefined(node.getModeName(), name);
        }
    }

    public void outAHostDeclaration(AHostDeclaration node) {
        final String name = node.getHostName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getHostName(), name);
        }

        if (hosts.put(name, node) != null) {
            errorRedefined(node.getHostName(), name);
        }
    }

    public void outANetworkDeclaration(ANetworkDeclaration node) {
        final String name = node.getNetworkName().getText();

        if (declarations.put(name, node) != null) {
            errorRedefined(node.getNetworkName(), name);
        }

        if (networks.put(name, node) != null) {
            errorRedefined(node.getNetworkName(), name);
        }
    }

    // Misc code

    private static void errorRedefined(Token token, String name) {
        throw new RuntimeException(
                "[" + token.getLine() + "," + token.getPos() + "] " + "Redefinition of " + name + ".");
    }

    public String toString() {
        final StringBuffer s = new StringBuffer();
        final String nl = System.getProperty("line.separator");

        s.append("Declarations:");
        s.append(nl);
        s.append(declarations);
        s.append(nl);

        s.append("Modules:");
        s.append(nl);
        s.append(modules);
        s.append(nl);

        s.append("Types:");
        s.append(nl);
        s.append(types);
        s.append(nl);

        s.append("Sensor ports:");
        s.append(nl);
        s.append(sensorPorts);
        s.append(nl);

        s.append("Actuator ports:");
        s.append(nl);
        s.append(actuatorPorts);
        s.append(nl);

        s.append("Output ports:");
        s.append(nl);
        s.append(outputPorts);
        s.append(nl);

        s.append("Tasks:");
        s.append(nl);
        s.append(tasks);
        s.append(nl);

        s.append("Drivers:");
        s.append(nl);
        s.append(drivers);
        s.append(nl);

        s.append("Modes:");
        s.append(nl);
        s.append(modes);
        s.append(nl);

        return s.toString();
    }
}
