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
package giotto.gdk;

import java.net.URL;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * @author M.A.A. Sanvido
 * @version SVGTree.java,v 1.11 2004/09/29 03:34:36 cxh Exp
 * @since Giotto 1.0.1
 */
class SVGTree {

    //private JEditorPane htmlPane;
    //private static boolean DEBUG = false;
    //private URL helpURL;

    public DefaultMutableTreeNode top;
    JTree tree;

    public SVGTree() {
        //Create the nodes.
        top = new DefaultMutableTreeNode("Modes");

        /*if (playWithLineStyle) {
          tree.putClientProperty("JTree.lineStyle", lineStyle);
          }*/

        /*Create the scroll pane and add the tree to it.
          JScrollPane treeView = new JScrollPane(tree);
        */

        /*Create the HTML viewing pane.
          htmlPane = new JEditorPane();
          htmlPane.setEditable(false);
          initHelp();
          JScrollPane htmlView = new JScrollPane(htmlPane);

          //Add the scroll panes to a split pane.
          JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
          splitPane.setTopComponent(treeView);
          splitPane.setBottomComponent(htmlView);

          Dimension minimumSize = new Dimension(100, 50);
          htmlView.setMinimumSize(minimumSize);
          treeView.setMinimumSize(minimumSize);
          splitPane.setDividerLocation(100); //XXX: ignored in some releases
          //of Swing. bug 4101306
          //workaround for bug 4101306:
          //treeView.setPreferredSize(new Dimension(100, 100));

          splitPane.setPreferredSize(new Dimension(500, 300));

          //Add the split pane to this frame.
          getContentPane().add(splitPane, BorderLayout.CENTER);
        */
    }

    public void defineListener(JTree tree) {
        //Listen for when the selection changes.
        this.tree = tree;
        tree.addTreeSelectionListener(new TreeSelection(tree));
    }

    /*private void displayURL(URL url) {
      try {
      htmlPane.setPage(url);
      } catch (IOException e) {
      System.err.println("Attempted to read a bad URL: " + url);
      }
      }*/

    public void deleteNodes() {
        top.removeAllChildren();
        int i = top.getChildCount();
        while (i > 0) {
            i--;
            top.remove(i);
        }
    }

    public void createNodes() {
        //                        GBase b;
        //                        Iterator li = GBase.root.iterator();
        //                        while (li.hasNext()) {
        //                                b = (GBase) li.next();
        //                                if (b instanceof GMode)
        //                                        createNodes((GMode) b);
        //                        }
        //                        tree.expandRow(0);
    }

    //                public void createNodes(GMode mode) {
    //                        DefaultMutableTreeNode prog = null;
    //                        DefaultMutableTreeNode task = null;
    //                        Task t;
    //                        Predicate p;
    //
    //                        t = mode.tasks.next;
    //                        prog = new DefaultMutableTreeNode(mode.name);
    //                        top.add(prog);
    //                        while (t.gtask != null) {
    //                                //original Tutorial
    //                                task =
    //                                        new DefaultMutableTreeNode(
    //                                                new ModeInfo(t.gtask.name, mode.name));
    //                                prog.add(task);
    //                                t = t.next;
    //                        }
    //                        p = mode.predicates.next;
    //                        while (p.gpred != null) {
    //                                //original Tutorial
    //                                task =
    //                                        new DefaultMutableTreeNode(
    //                                                new ModeInfo(p.gpred.name, mode.name));
    //                                prog.add(task);
    //                                p = p.next;
    //                        }
    //                }
    private class ModeInfo {
        public String modeName, taskName;
        public URL modeURL;
        public String prefix =
        "file:"
        + System.getProperty("user.dir")
                + System.getProperty("file.separator");
        public ModeInfo(String taskname, String mode) {
            modeName = mode;
            taskName = taskname;
            try {
                modeURL = new URL(prefix + modeName + ".svg");
            } catch (java.net.MalformedURLException exc) {
                System.err.println(
                        "Attempted to create a ModeInfo "
                        + "with a bad URL: "
                        + modeURL);
                modeURL = null;
            }
        }

        public String toString() {
            return taskName;
        }
    }

    private class TreeSelection implements TreeSelectionListener {
        JTree tree;
        TreeSelection(JTree t) {
            tree = t;
        }

        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null)
                return;
            Object nodeInfo = node.getUserObject();
            if (node.isLeaf()) {
                ModeInfo task = (ModeInfo) nodeInfo;
                //                                                GScanner.DebugLn("explorer " + task.modeURL);
                try {
                    Runtime.getRuntime().exec("explorer " + task.modeURL);
                } catch (Exception i) {
                    //                                                        GScanner.DebugLn("could not start SVG viewer");}
                }
            }
        }

    }
}
