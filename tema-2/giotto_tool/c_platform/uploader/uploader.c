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
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>

main(int argc, char *argv[]) {
    unsigned fd_file, fd_fifo, j;
    void *heap;

    unsigned fd_fileA, fd_fileB, i, k;
    char st[32];

    fd_file=open(argv[1], O_RDONLY);
    if (fd_file == -1) {
        if (errno == ENOENT)
            printf(" Error: File doesn't exist\n");
        else
            printf(" Error\n");
        exit(1);
    }
    fd_fifo=open("loaderpipe", O_WRONLY);

    read(fd_file, &j, sizeof(int));
    write(fd_fifo, &j, sizeof(int));
    j = j*(32+sizeof(int));
    heap = malloc(j);
    read(fd_file, heap, j);
    write(fd_fifo, heap, j);
    free(heap);

    read(fd_file, &j, sizeof(int));
    write(fd_fifo, &j, sizeof(int));
    j = j*4*sizeof(int);
    heap = malloc(j);
    read(fd_file, heap, j);
    write(fd_fifo, heap, j);
    free(heap);

    close(fd_fifo);
    close(fd_file);
}
