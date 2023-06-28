const admin = require('firebase-admin');
const serviceAccount = require('../firebaseConfig.json');

// Initialize the SDK with service account credentials
const serviceAccount = {
  "type": "service_account",
  "project_id": "my-application-2913c",
  "private_key_id": "112b5e4b490a10a81dd39ce9f57fde8f08f2d2b7",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCxrwqBIU8kvKw9\nBXJFc9wD1xRlr/A1tmFro+5IkiFuFOAkEsXUl+16K9JnEVkxbWqu03U89eXuG30x\n78Hj0RT4Pi1+Xncf1kMod0n7GYVtHZ3M2iaQm04xtt9xdgyIWpW8Tqh6lxcb3Oxj\nKGSD336/sIwEFXrP8IAz/oMHkTx9snsYJ85HRgmCjTmwk/TG78fC2+Qnk6I6Tokn\nE2BW8m7y4YGr0lxi+Sotr29fO6ZyurveNn7R2zT8RgHARtKh5GSDmN80WH7lcFF/\noy3rJMjQrE63F4WIrCTuRtZgxZXEqJP/sX445FOY/zBVDjRgz0cXUuXach4vLEe/\n4chewmHdAgMBAAECggEAASM4ARTObdOIaG6bE+9BNW7Af8MqhEmEjPzJxyyqVUsB\nwVwJE1/yolwv9r7MGvkn8Z0YdyQIOBKfJf1H45ZrLjNbCrVTnsBPge2Q63A3JA2d\nvO9DnRw6xK42oYJqOPWYLP0K7pf+NaHf4JBPDm9qn2hjs8NTG+GJYevV+RaDXuGi\n0ZHGysDVi5xP4332AN1pZplvYx5pgQigtRrnn+A51eVQ24RUo6YdWHrUaLONVfui\n8JQZ8Ra7tEI/38VyTLT75cUWVmhkV20fkEnvADrzay4+aAZefB5ImgB7xOZj42qn\naJvKKVyT5+LjYc+kFlLjtv0v0MuWICkjochiZcg0+QKBgQDtG0fvSwfsH3kJSzNH\n0npiBL4LV5E1E9+bcAO1lnhxgqHIDvViMPWO209RR/oEZ7k+n2zAg+M+dgmNxr6O\nUJTGNDKenXDplMVot+LShS0NX64kLedRh6pYgb2dtLPzZVG0UjPCh60UdlnnIk2Z\nRxvPNxThF+RfpdjhSOCZS/YiOQKBgQC/15k7P8nOAJRECidGgjZ+ZTBPwTwPOSpL\nYV8MDHpelABsE2sk0LI1bGb/ncNDP4ts8hcQm1kmk6/wyMfUXfNf0gb0rqlMuA4c\nvuf9vsVbRAyaiLcWKokP7Ftl5I+nRQbzptyymbt7QUcciYd34w7Pwf/NjeNc9+cT\niPFP6zpsxQKBgFtc6MIPMlVM51GqdW8Wbmv4kdZC/sPqDX6SkgtVmMF8aaRyByG3\n5dcPMKgMSQ2LfjjgK4KfD2BREWtdCq5B5KHfGSY/WnPXDJ5GRRODl5GQrcRpH7ot\nwXjrPUpYiAKgpvQJvMIXj5zT4d6LPEV0vgWBnv0BPNGSUVi1wT2wpH4ZAoGARI2L\ngSOjPozwbnveWxDO3k3UfLuFdb8swPEToN+YntFucTBjOi2lcPfCupcqno5HiW/d\nFB8fNWzTE0z8M4Q7IsOPnsIHV5Xx3Q3PItqX9awUgOhX3IDop5sDch7Bwrkk6c53\n0Q6kcz/5ZFY05X9q3Izrlw2b8K3Zkh5+dNuO7i0CgYB6rNaZNBHPNKsu13Fj3d1O\nt8VNjqexLmHDLBONNGNRduq181gExtAOV+Kp7wbqVcJA7XP9+HZFGfCAdoAriR8S\nRSNM4x1Pjy5uCqTPWkXgJ75POBqU2zNZ/LUIxjRmb+rxfU2zwmkuIhLW0tvT+AU8\nsY11OT6/DehdaSEuJVXEnQ==\n-----END PRIVATE KEY-----\n",
  "client_email": "firebase-adminsdk-cx0rw@my-application-2913c.iam.gserviceaccount.com",
  "client_id": "116756889519750499125",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-cx0rw%40my-application-2913c.iam.gserviceaccount.com",
  "universe_domain": "googleapis.com"
};
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

module.exports = admin;
