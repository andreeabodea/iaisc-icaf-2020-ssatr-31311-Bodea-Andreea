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

/*

Author: Christoph Kirsch, cm@eecs.berkeley.edu
Slobodan Matic, matic@eecs.berkeley.edu

*/

#ifndef _F_SPEC_
#define _F_SPEC_

#include "spec.h"

/* -------------------------------------------------------------------
 *
 * Driver specification
 *
 * ---------------------------------------------------------------- */

typedef void (*driver_code_type) (void);

typedef struct {
    char name[MAXNAME];
    driver_code_type call;
    unsigned protected;
} driver_type;

/* -------------------------------------------------------------------
 *
 * Condition specification
 *
 * ---------------------------------------------------------------- */

typedef unsigned (*condition_code_type) (void);

typedef struct {
    char name[MAXNAME];
    condition_code_type is_true;
    unsigned protected;
} condition_type;

/* -------------------------------------------------------------------
 *
 * Task specification
 *
 * ---------------------------------------------------------------- */

#ifdef OSEK
typedef int task_code_type;
#else
typedef void (*task_code_type) (void);
#endif

typedef struct {
    char name[MAXNAME];
    task_code_type schedule;
} task_type;

/* -------------------------------------------------------------------
 *
 * Trigger specification
 *
 * ---------------------------------------------------------------- */

#ifdef OSEK
typedef int e_machine_type;
#else
typedef void (*e_machine_type) (int /* trigger_type */);
#endif

typedef void (*enable_code_type) (e_machine_type, int);
typedef int (*save_enable_time_code_type) (void);
typedef unsigned (*trigger_code_type) (int, int);

typedef struct {
    char name[MAXNAME];

    enable_code_type enable;
    save_enable_time_code_type save;
    trigger_code_type is_active;
} trigger_type;

/* -------------------------------------------------------------------
 *
 * Port specification
 *
 * ---------------------------------------------------------------- */

typedef unsigned port_id_type;

typedef struct {
    char name[MAXNAME];
    void *port;
    unsigned size;
} port_type;

#endif
