# Desktop Manual

## Home window

The home window has easy access to the three main areas of the app:
assignment, employee and task management. It also contains a button to
close the window on the lower right side.

![](home-desktop.png)

## Employee Management

This window contains a list with the employees on the database, which
you can edit or fire. There are other buttons to generate reports and
paychecks that will be explainer later.

![](employee-management.png)

### Adding Employees

Next to the title of the list, there's a button to add new employees,
which will open a new modal window with the form to do so. 

![](employee-new.png)

Before adding the employee, the user must make sure to fill all the
information accordingly.

> The validations will be displayed while hovering on the 'save' button
{style="note"}

![](employee-new-validation.png)

### Editing Employees

To edit an employee, the user must either select an employee on the
list and then click on the 'Edit' button, or they can simply 
double-click on the item.

![](employee-edit.png)

> Neither the ID nor the DNI can be edited
{style="note"}

### Generating Paychecks

The user can click on the 'Paychecks' button on the top right side
of the employee manager window to access a modal in which you can
select the range of dates to generate the paycheck of **all** employees.

![](paycheck-generate.png)

The result will be similar to this:

![](paycheck-example.png)

This paycheck will then be sent to the mail of its corresponding
employee with this format:

![](paycheck-email.png)

### Employee Report and Expense Report

When clicking their respective buttons on the employee manager window,
the reports will be generated and stored on an internal folder.

This is what the employee report looks like:

![](employee-report.png)

And this is what the expense report looks like:

![](expense-report.png)

## Task Management

The window to manage tasks is much simpler, allowing to create, edit
and remove tasks, much like the employee one.

![](task-management.png)

> The flag icon indicates the priority of the task, with this order:
> * Gray = Least important
> * Green = Not too important
> * Yellow = Important
> * Red = Urgent 

### Filters

This could also be done on the employee management window, but we will
explain it now. On top of the list we have a selection of filters to
filter the list by the type of task that it is, like so:

![](task-filter.png)

### Adding Tasks

As with the employees, we find a button next to the title of the window
that will let us open the form to add new tasks.

![](task-new.png)

This way, the user can add normal tasks (With an employee already assigned
or not), but they can also create cleaning tasks by checking the first
checkbox that appears on top of the modal window.

![](task-new-clean.png)

Checking the checkbox will show a new selection to choose a dirty room
of the hotel, and it will also block the fields for description and type 
(as they will get filled automatically)

### Editing Tasks

The same form is reused to edit a task, and like with employees, the 
ID field is blocked. It's also worth mentioning that you can't edit
tasks which are already assigned and/or finished.

## Assignment Management

In order to assign tasks to the employees of the hotel, we have this
window which provides us with all the unassigned tasks.

![](assignment-management.png)

### Selecting employees

Once you select a task on the left, you will be able to click on the
'Assign' button (Or you can just double-click to open this modal):

![](assignment-assign.png)

> While you can initially choose any employee, an employee
> can only really be assigned to tasks that correspond to their
> specialty. Please refrain of feeling frustrated by this.
{style="warning"}

### Confirming the assignments

Once you select an employee, it will appear on the not-so-stylish 
list on the right:

![](assignment-assing2.png)

While on there, you can unassign them by selecting them and clicking
on the 'Unassign' button (No shortcuts for this one!) or you can
confirm all the assignments with the 'Confirm' button. Once this
button is clicked, an email will be sent to all the employees with
new tasks, like this:

![](assignment-email.png)

> If you leave the page before confirming the assignments, your
> work will be lost without a warning, so... Careful with that!
{style="warning"}

