/*
  Copyright (c) 2002-2004 The Regents of the University of California.
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
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * This file is part of SableCC.                             *
 * See the file "LICENSE" for copyright information and the  *
 * terms and conditions for copying, distribution and        *
 * modification of SableCC.                                  *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package giotto.giottoc;

import giotto.giottoc.node.TypedLinkedList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author M.A.A. Sanvido
 * @version MacroExpander.java,v 1.8 2004/10/01 01:10:09 cxh Exp
 * @since Giotto 1.0.1
 */
public class MacroExpander
{
    private static final String MACRO = "Macro:";
    private static final String lineSeparator = System.getProperty("line.separator");

    private Map macros = new TypedTreeMap(
            StringComparator.instance,
            StringCast.instance,
            ListCast.instance);

    public MacroExpander(Reader in) throws IOException
    {
        BufferedReader br = new BufferedReader(in);
        while (readInMacro(br));
        in.close();
    }

    private boolean readInMacro(BufferedReader in) throws IOException
    {
        String line;
        while ((line = in.readLine()) != null)
            {
                if (line.startsWith(MACRO))
                    {
                        String name = line.substring(MACRO.length());
                        List macro = new TypedLinkedList(StringCast.instance);

                        while ((line = in.readLine()) != null)
                            {
                                if (line.equals("$"))
                                    {
                                        macros.put(name, macro);
                                        return true;
                                    }

                                macro.add(line);
                            }

                        macros.put(name, macro);
                        return false;
                    }
            }

        return false;
    }

    public String toString()
    {
        return this.getClass().getName() + macros;
    }

    public void apply(BufferedWriter out, String macroName) throws IOException
    {
        apply(out, macroName, null);
    }

    public void apply(BufferedWriter out, String macroName, String[] arguments) throws IOException
    {
        List macro = (List) macros.get(macroName);

        for (ListIterator li = macro.listIterator(); li.hasNext();)
            {
                if (li.nextIndex() != 0)
                    {
                        out.newLine();
                    }

                String line = (String) li.next();
                char c;

                for (int i = 0; i < line.length(); i++)
                    {
                        if ((c = line.charAt(i)) == '$')
                            {
                                StringBuffer index = new StringBuffer();

                                while ((c = line.charAt(++i)) != '$')
                                    {
                                        index.append(c);
                                    }

                                if (index.length() == 0)
                                    {
                                        out.write('$');
                                    }
                                else
                                    {
                                        out.write(arguments[Integer.parseInt(index.toString())]);
                                    }
                            }
                        else
                            {
                                out.write(c);
                            }
                    }
            }
    }
}


