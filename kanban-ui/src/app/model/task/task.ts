import { Tag } from "./Tag";

export class Task {
  id: number;
  title: String;
  description: String;
  color: String;
  status: String;
  tags: Tag[];
  kanban_id: number;
}
