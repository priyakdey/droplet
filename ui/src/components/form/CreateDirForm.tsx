import { Button } from "@/components/ui/button.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage
} from "@/components/ui/form.tsx";
import { Input } from "@/components/ui/input.tsx";
import { createDirectory } from "@/services/directory.service.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { z } from "zod";

const createNewDirFormSchema = z.object({
  name: z.string()
    .min(1, "Name is required")
    .max(20, "Name is too long")
    .regex(/^[a-zA-Z0-9-]+$/, "Only letters, numbers, and hyphen (-) allowed.")
});

interface CreateDirFormPropsType {
  activeDirId: string;
  setActiveDirId: (id: string) => void;
  refreshDirectoryTree: () => void;
  onClose: () => void;
}

function CreateDirForm({
                         activeDirId,
                         setActiveDirId,
                         refreshDirectoryTree,
                         onClose
                       }: CreateDirFormPropsType) {
  const form = useForm<z.infer<typeof createNewDirFormSchema>>({
    resolver: zodResolver(createNewDirFormSchema),
    defaultValues: {
      name: ""
    }
  });

  const onSubmit = (values: z.infer<typeof createNewDirFormSchema>) => {
    const name = values.name;
    const parentId = activeDirId;
    createDirectory({ name, parentId })
      .then((res) => {
        form.reset();
        refreshDirectoryTree();
        setActiveDirId(res.id);
        toast.success("Directory created successfully", { duration: 5000 });
        onClose();
      }).catch((err) => {
      console.error(err);
      toast.error(err.message, { duration: 5000 });
    });

  };

  return (
    <div className="grid gap-4">
      <h4 className="font-medium leading-none">New Directory</h4>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField control={form.control} name="name" render={({ field }) => (
            <FormItem>
              <FormControl>
                <Input placeholder="Enter directory name" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}>
          </FormField>
          <Button className="submit-button" type="submit">
            Create
          </Button>
        </form>
      </Form>
    </div>
  );
}

export default CreateDirForm;