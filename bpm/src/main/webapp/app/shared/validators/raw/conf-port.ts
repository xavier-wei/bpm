export default function (port: string): boolean {
  let valid = false;

  if (isValidPort(port)) valid = true
  return valid;
}

const portRegex = /^(?!0)([0-9]{1,5})$/;
function isValidPort(port) {
  const portNumber = parseInt(port, 10);
  return portRegex.test(port) && portNumber >= 1 && portNumber <= 65535;
}
