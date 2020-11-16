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

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <fcntl.h>
#include <string.h>

/**

This code generates the disassembler for e_code.b

@author Arkadeb Ghosal ,arkadeb@eecs.berkeley.edu
*/

main()
{
    int fread, fwrite, number, count, arg1, arg2, arg3, ins, minUnitPeriod, modes, address;
    char string[20], modeName[32];

    fread = open ("e_code.b",O_RDONLY);
    fwrite = open ("Disassembler.dat", O_CREAT|O_WRONLY, 0777);

    read(fread, &modes, sizeof(int));
    sprintf (string, "No. of modes (%d)\n", modes);
    write(fwrite, string , strlen (string));


    for (count=0; count< modes; count++)

        {
            read (fread, modeName , 32);
            write(fwrite, modeName , strlen (modeName));

            sprintf (string, "\n");
            write(fwrite, string , strlen (string));

            read(fread, &address, sizeof(int));
            sprintf (string, "address (%d)\n", address);
            write(fwrite, string , strlen (string));


        }

    read(fread, &ins, sizeof(int));
    sprintf (string, "Instructions (%d)\n", ins);
    write(fwrite, string , strlen (string));

    read(fread, &minUnitPeriod, sizeof(int));
    sprintf (string, "minUnitPeriod (%d)\n", minUnitPeriod);
    write(fwrite, string , strlen (string));



    for (count=0; count< ins; count++)

        {
            read(fread, &number, sizeof(int));
            read(fread, &arg1, sizeof(int));
            read(fread, &arg2, sizeof(int));
            read(fread, &arg3, sizeof(int));


            switch (number)
                {
                case 0:
                    sprintf (string, "NOP \n");
                    break;
                case 1:
                    sprintf (string , "ENABLE (%d %d %d)\n", arg1, arg2, arg3);
                    break;
                case 2:
                    sprintf (string, "CALL (%d)\n", arg1);
                    break;
                case 3:
                    sprintf (string, "SCHEDULE (%d %d %d)\n", arg1, arg2, arg3);
                    break;
                case 4:
                    sprintf (string, "IF (%d %d %d)\n", arg1, arg2, arg3);
                    break;
                case 5:
                    sprintf (string, "JUMP (%d)\n", arg1);
                    break;
                case 6:
                    sprintf (string, "RETURN\n");
                    break;
                }
            write(fwrite, string , strlen (string));


        }

    close (fread);
    close (fwrite);

}











