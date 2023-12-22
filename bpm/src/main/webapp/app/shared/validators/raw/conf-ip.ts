export default function (ip: string): boolean {
  let valid = false;

  if (validateIPv4Addresses(ip) || validateIPv6Addresses(ip)) valid =true;

  return valid;
}


// 驗證IPv4
const ipv4Regex = /^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$/;

// 驗證IPv6
const ipv6Regex = /^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$|^([0-9a-fA-F]{1,4}:){1,7}(:[0-9a-fA-F]{1,4}){1,6}$|^([0-9a-fA-F]{1,4}:){1,6}:([0-9a-fA-F]{1,4}){1,6}$|^([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,7}$|^([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,8}$|^([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,9}$|^([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,10}$|^([0-9a-fA-F]{1,4}:){1}(:[0-9a-fA-F]{1,4}){1,11}$|^:((:[0-9a-fA-F]{1,4}){1,12}|:)$|^::$/;

 function validateIPv4Addresses(ip: any): any {

   const ipv4Array =  ip.split(',');

   for (let i = 0; i < ipv4Array.length; i++) {
     const trimmedValue = ipv4Array[i].trim(); // 去除可能的空格
     if (!ipv4Regex.test(trimmedValue)) {
       return false; // 如果有任何一个值不符合，返回 false
     }
   }

   return true; // 如果所有值都符合，返回 true
}

function validateIPv6Addresses(ip: any): any {
  const ipv6Array =  ip.split(',');

  for (let i = 0; i < ipv6Array.length; i++) {
    const trimmedValue = ipv6Array[i].trim(); // 去除可能的空格
    if (!ipv6Regex.test(trimmedValue)) {
      return false; // 如果有任何一个值不符合，返回 false
    }
  }

  return true; // 如果所有值都符合，返回 true
}
