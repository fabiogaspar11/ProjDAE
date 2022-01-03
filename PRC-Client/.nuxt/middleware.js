const middleware = {}

middleware['isAdministrator'] = require('..\\middleware\\isAdministrator.js')
middleware['isAdministrator'] = middleware['isAdministrator'].default || middleware['isAdministrator']

middleware['isHealthcareProfessional'] = require('..\\middleware\\isHealthcareProfessional.js')
middleware['isHealthcareProfessional'] = middleware['isHealthcareProfessional'].default || middleware['isHealthcareProfessional']

middleware['isPatient'] = require('..\\middleware\\isPatient.js')
middleware['isPatient'] = middleware['isPatient'].default || middleware['isPatient']

middleware['isPatientAccessingHisData'] = require('..\\middleware\\isPatientAccessingHisData.js')
middleware['isPatientAccessingHisData'] = middleware['isPatientAccessingHisData'].default || middleware['isPatientAccessingHisData']

export default middleware
