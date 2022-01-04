const middleware = {}

middleware['isAdministrator'] = require('..\\middleware\\isAdministrator.js')
middleware['isAdministrator'] = middleware['isAdministrator'].default || middleware['isAdministrator']

middleware['isAdminOrHealthcareProfAccessingHisData'] = require('..\\middleware\\isAdminOrHealthcareProfAccessingHisData.js')
middleware['isAdminOrHealthcareProfAccessingHisData'] = middleware['isAdminOrHealthcareProfAccessingHisData'].default || middleware['isAdminOrHealthcareProfAccessingHisData']

middleware['isHealthcareProfessional'] = require('..\\middleware\\isHealthcareProfessional.js')
middleware['isHealthcareProfessional'] = middleware['isHealthcareProfessional'].default || middleware['isHealthcareProfessional']

middleware['isHealthcareProfessionalAccessingHisData'] = require('..\\middleware\\isHealthcareProfessionalAccessingHisData.js')
middleware['isHealthcareProfessionalAccessingHisData'] = middleware['isHealthcareProfessionalAccessingHisData'].default || middleware['isHealthcareProfessionalAccessingHisData']

middleware['isPatient'] = require('..\\middleware\\isPatient.js')
middleware['isPatient'] = middleware['isPatient'].default || middleware['isPatient']

middleware['isPatientAccessingHisData'] = require('..\\middleware\\isPatientAccessingHisData.js')
middleware['isPatientAccessingHisData'] = middleware['isPatientAccessingHisData'].default || middleware['isPatientAccessingHisData']

export default middleware
