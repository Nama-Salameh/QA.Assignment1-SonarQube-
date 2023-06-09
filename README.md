# SimpleEditor

---------
# categories issues:

## 1.Product operation:
<p> Requirements that directly affect the daily operation of the software :
Correctness, reliability, efficiency, integrity, and usability</p>
 <p>1. error(276,27) printWriter must be closed (Resources should be closed): Failure to properly close resources will result in a resource leak which could bring first the application and then perhaps the box the application is on to their knees. So must close resources to prevent memory leakage, affects to the data and the availability of the system . </p>
<p>2. error(40,20) textPanel and menu variables must be private (Class variable fields should not have public accessibility): Public class variable fields do not respect the encapsulation principle and the values can change from anywhere in the code. Public variables effect on the code Integrity. </p>
<p>3. error(22,8) Editor parent variable name in class FindDialog (Child class fields should not shadow parent class fields) :Having a variable with the same name in two unrelated classes is fine, but do the same thing within a class hierarchy, and you’ll get confusion at best, chaos at worst. Effect on code Integrity.  </p>
<p>4. error(28,9) Matcher variable must be a transient (Fields in a "Serializable" class should either be transient or serializable): non-serializable data members could cause program crashes, and open the door to attackers. This effects on availability of the system. </p>
<p>5. error(92, 73) The use of deprecated function "CTRL_MASK":Once deprecated, classes, and interfaces, and their members should be avoided, rather than used, inherited or extended. Deprecation is a warning that the class or interface has been superseded, and will eventually be removed. The deprecation period allows you to make a smooth transition away from the aging, soon-to-be-retired technology. Effect code Reliability</p>
<p>6. error(92, 96) The use of deprecated function "SHIFT_MASK":Once deprecated, classes, and interfaces, and their members should be avoided, rather than used, inherited or extended. Deprecation is a warning that the class or interface has been superseded, and will eventually be removed. The deprecation period allows you to make a smooth transition away from the aging, soon-to-be-retired technology. Effect code Reliability</p>
<p>7. error (115, 13) & error (127, 13) Handling empty method body: There are several reasons for a method not to have a method body: It is an unintentional omission, and should be fixed to prevent an unexpected behavior in production. It is not yet, or never will be, supported. In this case an UnsupportedOperationException should be thrown.The method is an intentionally-blank override. In this case a nested comment should explain the reason for the blank override. Effects Efficiency</p>


## 2.Product revision:
<p>Requirements that affect the complete range of software maintenance activities
 Maintainability;  flexibility;  testability .</P>
 <p>1. error(181,8) && error(283,9) Conditional expressions are always tru : 
 Conditional expressions which are always true or false can lead to dead code.It is lead to major impairments of system maintainability .</p>
  <p>2. error(126,2) block of comminted-out line of code should be removed: Programmers should not comment out code as it bloats programs and reduces readability and maintainability.</p>
  <p>3. error(199,16) Incorrect exception handling: Using generic exceptions prevents calling methods from handling true, system-generated exceptions differently than application-generated errors.so It is lead to major impairments of system maintainability.</p>
  <p>4. error(42) copy,posts,cut,move should be static final : Public class variable fields do not respect the encapsulation principle and has main disadvantage ,Member values are subject to change from anywhere in the code and may not meet the programmer’s assumptions. so It is lead to major impairments of system Reliability.</p>
<p>5. error(42) Multiple variables should not be declared on the same line: Declaring multiple variables on one line is difficult to read.so It is lead to reduces system readability and maintainability.</p>
<p>6. error(166,5) && error(196,4) replace system.out by logger :If a program directly writes to the standard outputs, there is absolutely no way to comply with those requirements. so It is lead to reduces system Testability.</p>
 <p>7. error(228,60) declare constant inested of String duplicated: Duplicated string literals make the process of refactoring error-prone, since you must be sure to update all occurrences.so It is lead to reduces system flexibility.</P>
 <p>8. error(148,13) "actionPerformed" and "loadFile" are complexity functions (Cognitive Complexity of methods should not be too high) : Cognitive Complexity is a measure of how hard the control flow of a method is to understand. So these functions are difficult to maintains.</p>
<p>9. error(40,20) "TP" variable must be in a clear name, the ambiguous name effect on maintainability of the code. </p>
<p>10. error(249,60) "user.home" is duplicated string : Duplicated string literals make the process of refactoring error-prone, since you must be sure to update all occurrences. Duplicates effect on the code refactor (maintainability) </p>
<p>11. EdetorException error(5,13) :Inheritance tree of classes should not be too deep ,It is lead to reduces system maintainability and flexibility.
<p>12. error (67, 14) BuildMenu doesn't follow java coding convention: Shared naming conventions allow teams to collaborate efficiently. This rule checks that all method names match a provided regular expression. Effect maintainability</p>
<p>13. error (43, 16) changed should be static final constant or non-public: Public class variable fields do not respect the encapsulation principle and has three main disadvantages: Additional behavior such as validation cannot be added. The internal representation is exposed, and cannot be changed afterwards. Member values are subject to change from anywhere in the code and may not meet the programmer’s assumptions. By using private attributes and accessor methods (set and get), unauthorized modifications are prevented. Effects reliability</p>
<p>14. error (244, 6) & error (252, 5) replace the use of system.out by a logger:If a program directly writes to the standard outputs, there is absolutely no way to comply with those requirements. That’s why defining and using a dedicated logger is highly recommended. Effects Testability</p>
<p>15. error (253, 5) & error (266, 4) Extract nested try blocks: Nesting try/catch blocks severely impacts the readability of source code because it makes it too difficult to understand which block will catch which exception. Effects Maintainability</p>
<p>16. error (256, 17) using a dedicated exception instead of a generic one: Using such generic exceptions as Error, RuntimeException, Throwable, and Exception prevents calling methods from handling true, system-generated exceptions differently than application-generated errors. Effects Maintainability</p>
<p>17. error (298, 2) A "NULLPOINTEREXCEPTION" might be thrown here because writer can be null: A reference to null should never be dereferenced/accessed. Doing so will cause a NullPointerException to be thrown. At best, such an exception will cause abrupt program termination. At worst, it could expose debugging information that would be useful to an attacker, or it could allow an attacker to bypass security measures.: Effects Maintainability</p>
<p>18. error (311, 14) unused private method "saveAsText": private methods that are never executed are dead code: unnecessary, inoperative code that should be removed. Cleaning out dead code decreases the size of the maintained codebase, making it easier to understand the program and preventing bugs from being introduced. Effects Maintainability</p>

## 3.Product transition :
<p> adaptation of software to other environments and its interaction with other software systems
Portability;  Reusability;  Interoperability .</p>

## No issues in sounarlint :
![bandicam 2023-03-18 00-14-35-481](https://user-images.githubusercontent.com/113710703/226062763-6b150116-8e02-4afa-9057-0064dd663107.jpg)

## false Negative issues:
 <p>1. in FindDialog class (46) : setLayout(new GridLayout(3, 1)); must 3 and 1 be replaced by magic numbers , It gives the number meaning so anyone can read it and know what the number is used for.</p>
 <P>2. in Editor class : sell variable must be in a clear and meaningful name (selectAll) </P>
 <P>3. in Editor class : jmfile variable must be in a clear and meaningful name (jMFile) </P> 
 <p>4.  in Editor class : saveas variable must follow java conventions (saveAs) </p>
 <P>5. in Editor class : in "loadFile" and "actionPerformed" methods the condition if(changed) duplicated 2 times inside each other, and the internal if having else sentence (this unreachable code) and didn't discover it. </P>
 <p>6. Strings such as "Cannot write file!" , "Cannot read file !" , "You have reached the end of the file" --> must be in a static final variables </p>

## false Positive issues:
The code does not contain false Positive issues.

