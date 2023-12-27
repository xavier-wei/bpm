export default function (ports: string): boolean {
  const portArray = ports.split(',');

  for (const port of portArray) {
    if (!isValidPort(port.trim())) {
      return false; // 如果任何一个port無效，返回 false
    }
  }

  return true; // 如果所有port都有效，返回 true
}

const portRegex = /^(?!0)([0-9]{1,5})$/;

function isValidPort(port: string): boolean {
  const portNumber = parseInt(port, 10);
  return portRegex.test(port) && portNumber >= 1 && portNumber <= 65535;
}
