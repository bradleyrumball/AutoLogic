# AutoLogic
Automated Tool Support for Logic Coverage of Java Code

Team:
- Bradley D Rumball (brumball1@sheffield.ac.uk)
- Harley Everett (heverett1@sheffield.ac.uk)

>Brief: https://vle.shef.ac.uk/bbcswebdav/pid-4948873-dt-content-rid-33184548_1/courses/COM3529.B.224696/assignment.pdf

# TODO

#### Analysis of the Method Under Test ####
- [ ] Requires significant manual input 😟/😭
- [ ] Requires some manual input, partial automated support 🙂/😐
- [ ] Method under test fully parsed, with conditions and structure of predicates extracted and analysed 😁

#### Test Requirement Generation ####
- [ ] Simple coverage criterion implemented (e.g., Condition Coverage) 😟/😭
- [ ] Advanced coverage criterion implemented (e.g., Restricted or Cor-related MCDC) 🙂/😐
- [ ] Multiple criteria implemented 😁

#### Instrumentation ####
- [ ] Supplies a simple API that is applied within code blocks 😟/😭
- [ ] Supplies an advanced API that is applied within conditions 🙂/😐
- [ ] Method under test is automatically parsed and intrumented 😁

#### Test Data Generation ####
- [ ] Very basic random number generation 😭
- [ ] Configurable random number generation (e.g., input parameters can be configured with upper and lower bounds) 😟
- [ ] Advanced random number generation (e.g., can be used to generate non-numerical inputs randomly, such as strings and other types, like objects) 😐
- [ ] Advanced random number generation (e.g., can use example inputs as the basis of seeds, similar to fuzzing), or, applies a search-based technique “out of the box” (e.g., the AVMf) 🙂
- [ ] Applies  own  search-based  method  (e.g.,  implemented  own  evolutionary algorithm) or similarly advanced technique 😁

#### Coverage Level Computation and Reporting ####
- [ ] Coverage level computed for simple criterion as implemented for (2)above 😐/😟/😭
- [ ] Coverage  level  computed  and  individual  uncovered  elements  re-ported 😁/🙂

#### Test Suite Output ####
- [ ] Simple output of inputs to the command line 😐/😟/😭
- [ ] Writes out JUnit Java code that can be compiled separately and run 😁/🙂

#### GitHub Repo andREADME.md ####
- [ ] Problems with repo (e.g., files missing) and/or instructions deficient 😐/😟/😭
- [ ] Everything works and can be setup from the repo, according to instructions supplied in theREADME.md 🙂
- [ ] README.md especially  well-polished,  installation  and  running  tool worked flawlessly 😁

Key:
😁 = 1st
🙂 = 2:1 
😐 = 2:2
😟 = 3rd
😭 = Pass