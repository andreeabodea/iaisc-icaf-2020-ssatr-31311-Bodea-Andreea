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
import giotto.giottoc.node.AActualPort;
import giotto.giottoc.node.AFormalPort;
import giotto.giottoc.node.AStatePort;
import giotto.giottoc.node.Node;
import giotto.giottoc.node.NodeCast;
import giotto.giottoc.node.Token;

import java.util.HashSet;
import java.util.Map;
/**

This class implements a scoped symbol table.

@author Christoph Kirsch
@version ScopedSymbolTable.java,v 1.9 2004/09/29 03:52:26 cxh Exp
@since Giotto 1.0.1
*/
public class ScopedSymbolTable extends DepthFirstAdapter
{
    private SymbolTable symbolTable;

    public final Map declarations = new TypedTreeMap(
            StringComparator.instance,
            StringCast.instance,
            NodeCast.instance);

    public final HashSet actualPorts = new HashSet();

    public ScopedSymbolTable(SymbolTable symbolTable)
    {
        this.symbolTable = symbolTable;
    }

    // Action code

    public void outAFormalPort(AFormalPort node)
    {
        final String name = node.getPortName().getText();

        if (symbolTable.declarations.containsKey(name))
            {
                errorRedefined(node.getPortName(), name);
            }

        if (declarations.put(name, node) != null)
            {
                errorMultipleUse(node.getPortName(), name);
            }
    }

    public void outAActualPort(AActualPort node)
    {
        final String name = node.getPortName().getText();

        if (!symbolTable.declarations.containsKey(name))
            {
                errorUndefined(node.getPortName(), name);
            }
        else
            {
                final Node declarationNode = (Node) symbolTable.declarations.get(name);

                if (declarations.put(name, declarationNode) != null)
                    {
                        errorMultipleUse(node.getPortName(), name);
                    }

                actualPorts.add(name);
            }
    }

    public void outAStatePort(AStatePort node)
    {
        final String name = node.getPortName().getText();

        if (symbolTable.declarations.containsKey(name))
            {
                errorRedefined(node.getPortName(), name);
            }

        if (declarations.put(name, node) != null)
            {
                errorMultipleUse(node.getPortName(), name);
            }
    }

    // Misc code

    private static void errorRedefined(Token token, String name)
    {
        throw new RuntimeException(
                "[" + token.getLine() + "," + token.getPos() + "] " +
                "Redefinition of " + name + ".");
    }

    private static void errorMultipleUse(Token token, String name)
    {
        throw new RuntimeException(
                "[" + token.getLine() + "," + token.getPos() + "] " +
                "Multiple use of " + name + ".");
    }

    private static void errorUndefined(Token token, String name)
    {
        throw new RuntimeException(
                "[" + token.getLine() + "," + token.getPos() + "] " +
                name + " undefined.");
    }

    public String toString()
    {
        final StringBuffer s = new StringBuffer();
        final String nl = System.getProperty("line.separator");

        s.append("Declarations:");
        s.append(nl);
        s.append(declarations);
        s.append(nl);

        return s.toString();
    }
}

