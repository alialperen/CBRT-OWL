
# YTU-CE Semantic Web Graduate Course-HW Assignment 3

	This is a maven project. 
	To get required libraries for the project please right click-Maven-Update Project...
	Names of required libraries can be found in depencies tags in pom.xml.
* src
	* tr.gov.cbrt.hwOwl.model
		- Class Models 
	* tr.gov.cbrt.hwOwl.model.Impl
		- Class Model Implementions
	* tr.gov.cbrt.hwOwl.service
		- HierarchyService  -gets Hierarchy from an owl file 
		- InferenceService  -gets Inference from an owl file
		- ProcessingService -Queries, combines and validates owl files
	* tr.gov.cbrt.hwOwl.service.Impl
		-Implements of Service Interfaces
	* tr.gov.cbrt.hwXml.test
		- Junit tests of the services 
			- AllTests.java (Runs all tests)
			- To test all 5 owl files ; indice of ProcessingService.OWL_FILES[0] array can be changed 0 to 4.
			- To test SPARQL queries, indice of ProcessingService.QUERY_FILES[0]array can be changed 0 to 2.
* bin
	* Class files
* txtdata
	* Outputs of service methods
* owldata
	* 5 owl files 
* query
	* 3 SPARQL query files
