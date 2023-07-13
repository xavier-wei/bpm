export default function (file: File | null): boolean {
  if (file) {
    let width = 0;
    let height = 0;
    const fileReader = new FileReader();
    fileReader.onload = e => {
      const image = new Image();
      image.onload = () => {
        width = image.width;
        height = image.height;
        console.log(width < height);
      }
      image.src = e.target.result as string;
    };
    fileReader.readAsDataURL(file);
    return width <= height;
  } else {
    return true;
  }
}