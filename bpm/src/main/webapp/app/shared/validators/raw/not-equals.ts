export default function (compareValue: () => any) {
  return value => {
    if (value instanceof Date) {
      if (Array.isArray(compareValue())) {
        return !value || !compareValue() || compareValue().every(i => i.getTime() !== value.getTime());
      } else {
        return !value || !compareValue() || value.getTime() !== compareValue().getTime();
      }
    } else {
      if (Array.isArray(compareValue())) {
        return !value || !compareValue() || compareValue().every(i => i !== value);
      } else {
        return !value || !compareValue() || value !== compareValue();
      }
    }
  }
}