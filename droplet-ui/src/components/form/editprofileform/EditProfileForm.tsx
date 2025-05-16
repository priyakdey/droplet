import TimezoneSelect from "@/components/timezoneslect/TimezoneSelect.tsx";
import { Button } from "@/components/ui/button.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage
} from "@/components/ui/form.tsx";
import { Input } from "@/components/ui/input.tsx";
import { Label } from "@/components/ui/label.tsx";
import {
  Select,
  SelectContent,
  SelectTrigger,
  SelectValue
} from "@/components/ui/select.tsx";
import useAuth from "@/hooks/useAuth.ts";
import useProfile from "@/hooks/useProfile.ts";
import { getAllAvailableTimezones } from "@/service/timezoneService.ts";
import { nameSchema, timezoneSchema } from "@/types/formSchema.ts";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { z } from "zod";
import "./EditProfileForm.css";

const editProfileSchema = z.object({
  name: nameSchema,
  timezone: timezoneSchema
});

interface EditProfileFormPropsType {
  closeSheet: () => void;
}

function EditProfileForm({ closeSheet }: EditProfileFormPropsType) {
  const [ timezones, setTimeZones ] = useState<string[]>([]);
  const [ isLoaded, setIsLoaded ] = useState<boolean>(false);
  const { name, timezone } = useProfile();
  const { token } = useAuth();

  useEffect(() => {
    if (isLoaded) return;

    getAllAvailableTimezones(token!)
      .then(data => {
        setTimeZones(data);
        setIsLoaded(true);
      })
      .catch(error => {
        const description = typeof error.cause === "string"
          ? error.cause : "An unknown error occurred";
        toast.error(error.message, { description: description });
      });
  }, []);

  const form = useForm<z.infer<typeof editProfileSchema>>({
    resolver: zodResolver(editProfileSchema),
    defaultValues: {
      name: name!,
      timezone: timezone!
    }
  });

  function handleEditProfile(values: z.infer<typeof editProfileSchema>) {
    console.log("editProfile", values);
    closeSheet();
  }

  return (
    <Form {...form}>
      <form className="edit-profile-form"
            onSubmit={form.handleSubmit(handleEditProfile)}>
        {/* name */}
        <FormField control={form.control} name="name" render={({ field }) => (
          <FormItem>
            <FormControl>
              <div className="edit-profile-form-input-container">
                <Label htmlFor="name"
                       className="edit-profile-form-label"
                >
                  Name
                </Label>
                <Input id="name" type="text" autoFocus={false}
                       autoComplete={"off"} {...field} />
              </div>

            </FormControl>
            <FormMessage />
          </FormItem>
        )}>
        </FormField>

        {/* timezone */}
        <FormField control={form.control} name="timezone"
                   render={({ field }) => (
                     <FormItem>
                       <FormControl>
                         <div className="edit-profile-form-input-container">
                           <Label id="timezone-label"
                                  className="edit-profile-form-label"
                           >
                             Timezone
                           </Label>
                           <Select aria-labelledby="timezone-label"
                                   value={field.value}
                                   onValueChange={field.onChange}
                           >
                             <SelectTrigger>
                               <SelectValue placeholder="Select Timezone" />
                             </SelectTrigger>
                             <SelectContent>
                               <TimezoneSelect timezones={timezones} />
                             </SelectContent>
                           </Select>
                         </div>
                       </FormControl>
                       <FormMessage />
                     </FormItem>
                   )}>
        </FormField>
        {/* Submit */}
        <Button type="submit" variant="default">
          Save Changes
        </Button>
      </form>
    </Form>
  );

}

export default EditProfileForm;