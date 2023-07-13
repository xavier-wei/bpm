/**
 * Check if the length of userId is bigger than 8 and can only contain numbers and at least one alphbat
 */

export default function (userId: string): boolean {
  // only alphabet and numbers
  const regex = new RegExp(/^([a-zA-Z0-9]*([a-zA-Z]{1,}?)+([a-zA-Z0-9]*$))/);
  if (userId) {
    let result = regex.test(userId);
    if (result === true && userId.length >= 8) {
      return true;
    } else {
      return false;
    }
  }
}
