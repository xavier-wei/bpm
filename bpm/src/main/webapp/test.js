let aaa = new Map

aaa.set("key","test")
aaa = Array.from(aaa);
aaa =Object.fromEntries(aaa)
console.log('aaa',aaa)
console.log('aaasca',JSON.stringify(aaa))
