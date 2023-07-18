
export interface IAuthorModel {
  file?: File | null;
  size?: string | null;
  upDataTime?: Date | null;
  author?: string | null;
  attachmentDescription?: string | null;
}


export class AuthorModel implements IAuthorModel {

  constructor(
    public file?: File | null,
    public size?: string | null,
    public updateTime?: Date | null,
    public author?: string | null,
    public attachmentDescription?: string | null,
  ){}

}
