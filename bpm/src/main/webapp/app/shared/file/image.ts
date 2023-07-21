export function loadImage({file, maxSize}) {
    return new Promise((resolve, reject) => {
        if (!file) {
            reject("file is null")
            return
        }
        const reader = new FileReader()
        reader.onload = function (e) {
            const img = new Image();
            img.onload = async function () {
                const canvas = document.createElement("canvas");
                let size = maxSize ? maxSize : 600
                let fileSize = 1000001;
                let newImage: string;
                let newFile: File;

                do {
                    let width
                    let height
                    if (img.width > img.height) {
                        let scale = size / img.width
                        if (scale > 1) scale = 1
                        width = img.width * scale
                        height = img.height * scale
                    } else {
                        let scale = size / img.height
                        if (scale > 1) scale = 1
                        width = img.width * scale
                        height = img.height * scale
                    }
                    canvas.width = width
                    canvas.height = height
                    const ctx = canvas.getContext("2d");
                    ctx.drawImage(img, 0, 0, width, height);
                    newImage = canvas.toDataURL("image/jpeg")

                    const data = new Date()
                    const res: Response = await fetch(newImage);
                    const blob: Blob = await res.blob();
                    newFile = new File([blob], `INS_image-${parseInt(`${data.getTime() / 1000}`)}.${file.name.split('.')[1]}`, {type: "image/png"})
                    fileSize = newFile.size
                    size = size * 0.9
                } while (fileSize > 1000000)
                resolve({image: newImage, file: newFile})
            }
            img.src = e.target.result as string;
        };
        reader.readAsDataURL(file)
    })
}
