export default function (compareValue) {
  return value => {
    return !!compareValue() ? !!value : !value;
  };
}